import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpfetcherService } from '../services/httpfetcher.service';
import { FetchedTodos } from '../interfaces/fetched-todos';

@Component({
  selector: 'app-todo-widget',
  templateUrl: './todo-widget.component.html',
  styleUrls: ['./todo-widget.component.css']
})
export class TodoWidgetComponent implements OnInit, OnDestroy {

  private httpFetchedTodos: Subscription;

  public todosList: FetchedTodos;
  public errorMessage: string;

  constructor(private http: HttpfetcherService) { }

  ngOnInit() {
    const observer = {
      next: (resp: FetchedTodos) => {
        this.todosList = resp;
      },
      error: () => {
        this.errorMessage = 'Some error occured';
      }
    }
    this.httpFetchedTodos = this.http.getTodos().subscribe(observer);
  }

  ngOnDestroy() {
    if(this.httpFetchedTodos){
      this.httpFetchedTodos.unsubscribe();
    }
  }

}
