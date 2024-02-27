import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { Person } from "src/app/shared/models/person.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class PersonService {

    constructor(
        private httpClient: HttpClient
    ) { }

    private _person$ = new BehaviorSubject<Person>(new Person);
    public get person$(): Observable<Person> {
        return this._person$.asObservable();
    }

    public getPersonById(personId: number) {
        this.httpClient.get<Person>(`${environment.msPatientUrl}/persons/${personId}`).pipe(
            tap(person => {
                this._person$.next(person);
            })
        ).subscribe();
    }

    public getPersonByEmail(personEmail: string): Observable<Person> {
        return this.httpClient.get<Person>(`${environment.msPatientUrl}/persons/email/${personEmail}`);
    }

    public createNewPerson(newPerson: Person) {
        return this.httpClient.post<{ id: number }>(`${environment.msPatientUrl}/person`, newPerson).pipe(
            map(newPersonId => newPersonId.id),
            catchError(() => of(0))
        );
    }

    public updatePersonById(personId: number, personUpdate: Person) {
        return this.httpClient.put(`${environment.msPatientUrl}/persons/${personId}`, personUpdate).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}