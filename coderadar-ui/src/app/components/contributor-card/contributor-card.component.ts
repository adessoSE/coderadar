import {Component, ElementRef, HostListener, Inject, Input, OnInit} from '@angular/core';
import {Contributor} from '../../model/contributor';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';

interface ContributorDialogData{
  contributor: Contributor;
}

@Component({
  selector: 'app-contributor-card',
  templateUrl: './contributor-card.component.html',
  styleUrls: ['./contributor-card.component.css']
})
export class ContributorCardComponent implements OnInit {

  @HostListener('document:click',['$event'])
  clickout(event){
    if(this.clickoutHandler){
      this.clickoutHandler(event);
    }
  }

  clickoutHandler: Function;

  dialogRef: MatDialogRef<ContributorDialogComponent>;

  @Input()
  contributor: Contributor;
  @Input()
  noAvatar: boolean = false;
  constructor(public dialog: MatDialog) { }

  openDialog(): void{
    setTimeout(() => {
      this.dialogRef = this.dialog.open(ContributorDialogComponent, {
        minWidth: '150px',
        data:{contributor: this.contributor}

      });

      this.dialogRef.afterOpened().subscribe(()=>{
        this.clickoutHandler = this.closeDialogFromClickout;
      });
    });
  }

  closeDialogFromClickout(event: MouseEvent){
    if (!this.dialogRef.componentInstance)return;
    const matDialogContainer = this.dialogRef.componentInstance.hostElement.nativeElement.parentElement;
    const rect = matDialogContainer.getBoundingClientRect();
    if(event.clientX <= rect.left || event.clientX >= rect.right || event.clientY <= rect.top || event.clientY >= rect.bottom){
      this.dialogRef.close();
    }
  }


  ngOnInit() {

  }

}

@Component({
  selector: 'app-contributor-dialog',
  templateUrl: './contributor-dialog.html',
  styleUrls: ['contributor-dialog.css']
})
export class ContributorDialogComponent implements OnInit {

  contributor: Contributor;
  avatarUrl: string;

  constructor(
    public hostElement: ElementRef,
    public dialogRef: MatDialogRef<ContributorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ContributorDialogData
  ) {
    this.contributor = data.contributor;
  }

  getAliases(): string[]{
    return this.contributor.names.filter(value => value !== this.contributor.displayName);
  }

  ngOnInit() {
  }

}
