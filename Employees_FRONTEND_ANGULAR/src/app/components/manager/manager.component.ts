import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CoreService } from 'src/app/common/services/core.service';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { Empleado } from 'src/app/common/interfaces/empleado';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.scss']
})
export class ManagerComponent implements OnInit{
  employeeForm: FormGroup;

  minDateNacimiento = new Date(1900, 0, 1);
  minDateIngreso = new Date(1970, 0, 1);
  maxDateNacimiento = new Date(new Date().getFullYear() - 18, new Date().getMonth(), new Date().getDate());
  maxDateIngreso = new Date();

  patternNameApellido = new RegExp(/^[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+(\s[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+){0,2}$/);
  patternEmail = new RegExp(/^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/);

  constructor(
    private _fb: FormBuilder,
    private _employeeService: EmployeeService,
    private _dialogRefForm: MatDialogRef<ManagerComponent>,
    private _coreService: CoreService,
    private _dialogError: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: Empleado
    ) { 
      this.employeeForm = this._fb.group({
        nroDocumento: new FormControl('', [Validators.required, Validators.min(1000000), Validators.max(99999999)]),
        email: new FormControl('', [Validators.required, Validators.email, Validators.pattern(this.patternEmail)]),
        nombre: new FormControl('', [Validators.required, Validators.pattern(this.patternNameApellido), Validators.minLength(2), Validators.maxLength(80)]),
        apellido: new FormControl('', [Validators.required, Validators.pattern(this.patternNameApellido), Validators.minLength(2), Validators.maxLength(80)]),
        fechaNacimiento: new FormControl('', [Validators.required]),
        fechaIngreso: new FormControl('', [Validators.required])
      })
    }

    ngOnInit(): void {
      if(this.data) {
        this.employeeForm.patchValue(this.data);
      }
    }

    onFormSubmit() {
      if(this.employeeForm.valid) {

        if(this.data) {
          this._employeeService.updateEmployee(this.data.id, this.employeeForm.value).subscribe({
            next: (data) => {
              this._coreService.openSnackBar('Actualización completada!');
              this._dialogRefForm.close(true);
            },
            error: (error) => {
              this.openDialogError(error.error.message);
            }
          })
        } else {
          this._employeeService.addEmployee(this.employeeForm.value).subscribe({
            next: (data) => {
              this._coreService.openSnackBar('Nuevo empleado creado!');
              this._dialogRefForm.close(true);
            },
            error: (error) => {
              this.openDialogError(error.error.message);
            }
          })
        }
      }
    }

    openDialogError(message: string) {
      const _dialogRefError = this._dialogError.open(ErrorDialogComponent, {
        data: message
      });
    }
  }
