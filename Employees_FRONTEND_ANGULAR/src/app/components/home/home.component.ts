import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { ManagerComponent } from '../manager/manager.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CoreService } from 'src/app/common/services/core.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { Empleado } from 'src/app/common/interfaces/empleado';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'nroDocumento',
    'nombre', 
    'apellido', 
    'email', 
    'fechaNacimiento', 
    'fechaIngreso', 
    'fechaCreacion',
    'actions'
    ];

    dataSource: MatTableDataSource<Empleado> = new MatTableDataSource<Empleado>([]);
  
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

  constructor (
    private _dialogForm: MatDialog, 
    private _employeeService: EmployeeService,
    private _coreService: CoreService,
    private _dialogError: MatDialog
    ) { }

  ngOnInit(): void {
    this.getEmployees();
  }

  openDialog() {
    const _dialogRefForm = this._dialogForm.open(ManagerComponent);
    _dialogRefForm.afterClosed().subscribe({
      next: (value) => {
        if(value){
          this.getEmployees();
        }
      }
    });
  }

  openEditDialog(data : Empleado) {
    const _dialogRefForm = this._dialogForm.open(ManagerComponent, {
      data
    });
    _dialogRefForm.afterClosed().subscribe({
      next: (value) => {
        if(value){
          this.getEmployees();
        }
      }
    });
  }

  confirmDelete(id: number){
    const _dialogRefForm = this._dialogForm.open(ConfirmDialogComponent)
    _dialogRefForm.afterClosed().subscribe({
      next: (value) => {
        if(value){
          this.deleteEmployee(id);
        }
      },
      error: (error) => {
        this.openDialogError(error.error.error);
      }
    });
  }

  getEmployees() {
    this._employeeService.getEmployees().subscribe({
      next: (data: Empleado[]) => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (error) => {
        console.log(error);
        this.dataSource.data = [];
        this.openDialogError('Hubo un problema con el servidor al obtener los datos');
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  deleteEmployee(id: number){
    this._employeeService.deleteEmployee(id).subscribe({
      next: (data) => {
        this._coreService.openSnackBar('Empleado eliminado correctamente');
        this.getEmployees();
      },
      error: (error) => {
        this.openDialogError(error.error.error);
        this.getEmployees();
      }
    });
  }

  openDialogError(message: string) {
    const _dialogRefError = this._dialogError.open(ErrorDialogComponent, {
      data: message
    });
  }
}
