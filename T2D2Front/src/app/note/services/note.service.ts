import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note } from '../models/note.model';
import { environment } from 'src/app/environments/environment';
import { BehaviorSubject, Observable, catchError, map, of, tap } from 'rxjs';

@Injectable()
export class NoteService {

  constructor(
    private httpClient: HttpClient
  ) { }

  private _notes$ = new BehaviorSubject<Note[]>([]);
  public get notes$(): Observable<Note[]> {
    return this._notes$.asObservable();
  }

  private _note$ = new BehaviorSubject<Note>(new Note);
  public get note$(): Observable<Note> {
    return this._note$.asObservable();
  }

  public getNotes(personId: number) {
    this.httpClient.get<Note[]>(`${environment.msPractitionnerUrl}/notes/persons/${personId}`).pipe(
      tap(notes => {
        this._notes$.next(notes);
      })
    ).subscribe();
  }

  public getNoteById(noteId: string) {
    this.httpClient.get<Note>(`${environment.msPractitionnerUrl}/notes/${noteId}`).pipe(
      tap(note => {
        this._note$.next(note);
      })
    )
  }

  public createNewNote(newNote: Note) {
    return this.httpClient.post(`${environment.msPractitionnerUrl}/note`, newNote).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  public updateNoteById(noteId: string, noteUpdate: Note) {
    return this.httpClient.put(`${environment.msPractitionnerUrl}/notes/${noteId}`, noteUpdate).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  public deleteNoteById(noteId: string) {
    return this.httpClient.delete(`${environment.msPractitionnerUrl}/notes/${noteId}`);
  }
}
