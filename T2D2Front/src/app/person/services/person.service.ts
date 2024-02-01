import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of, tap } from "rxjs";
import { Person } from "src/app/core/models/person.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class PersonService {

    constructor(
        private httpClient: HttpClient
    ) {}

    public getPersonById(personId: number): Observable<Person> {
        return this.httpClient.get<Person>(`${environment.apiUrl}/persons/${personId}`);
    }

    public getPersonByEmail(personEmail: string): Observable<Person> {
        return this.httpClient.get<Person>(`${environment.apiUrl}/persons/email/${personEmail}`);
    }

    public getPersonEmailByPersonId(personId: number): Observable<string> {
        return this.httpClient.get<string>(`${environment.apiUrl}/persons/${personId}/email`);
    }

    public createNewPerson(newPerson: Person) {
        return this.httpClient.post<{id: number}>(`${environment.apiUrl}/person`, newPerson).pipe(
          map(newPersonId => newPersonId.id) ,
          catchError(() => of(0))
        );
    }

    public updatePersonById(personId: number, personUpdate: Person) {
        return this.httpClient.put(`${environment.apiUrl}/persons/${personId}`, personUpdate).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}