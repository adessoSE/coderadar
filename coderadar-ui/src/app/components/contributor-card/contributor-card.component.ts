import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Contributor} from '../../model/contributor';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ContributorDialogComponent} from './contributor-dialog.component';

@Component({
  selector: 'app-contributor-card',
  templateUrl: './contributor-card.component.html',
  styleUrls: ['./contributor-card.component.css']
})
export class ContributorCardComponent implements OnInit {
  constructor(public dialog: MatDialog) { }

  clickoutHandler: (event: MouseEvent) => void;

  dialogRef: MatDialogRef<ContributorDialogComponent>;

  @Input()
  contributor: Contributor;
  @Input()
  noAvatar = false;

  @HostListener('document:click', ['$event'])
  clickout(event) {
    if (this.clickoutHandler) {
      this.clickoutHandler.call(event);
    }
  }

  openDialog(): void {
    setTimeout(() => {
      this.dialogRef = this.dialog.open(ContributorDialogComponent, {
        minWidth: '150px',
        data: {contributor: this.contributor}

      });

      this.dialogRef.afterOpened().subscribe(() => {
        this.clickoutHandler = this.closeDialogFromClickout;
      });
    });
  }

  closeDialogFromClickout(event: MouseEvent) {
    if (!this.dialogRef.componentInstance) {return; }
    const matDialogContainer = this.dialogRef.componentInstance.hostElement.nativeElement.parentElement;
    const rect = matDialogContainer.getBoundingClientRect();
    if (event.clientX <= rect.left || event.clientX >= rect.right || event.clientY <= rect.top || event.clientY >= rect.bottom) {
      this.dialogRef.close();
    }
  }


  ngOnInit() {

  }

}

