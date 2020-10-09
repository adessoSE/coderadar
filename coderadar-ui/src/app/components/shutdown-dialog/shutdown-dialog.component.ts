import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-shutdown-dialog',
  templateUrl: './shutdown-dialog.component.html'
})
export class ShutdownDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ShutdownDialogComponent>,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
