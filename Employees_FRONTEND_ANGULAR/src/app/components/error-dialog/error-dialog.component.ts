import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.scss']
})
export class ErrorDialogComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: String
  ) { 

  }

  ngOnInit(): void {
    if(!this.data){
      this.data = 'No podemos realizar la operación en este momento. Intente nuevamente más tarde.'
    }
  }
}
