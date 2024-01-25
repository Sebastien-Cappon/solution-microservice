import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { ResidenceValue } from "../../core/models/residence.model";
import { Address } from "src/app/core/models/address.model";

@Injectable()
export class ResidenceService {

    constructor(
        private httpClient: HttpClient
    ) {}

    public wayTypes: string[] = ['Ave.', 'Rd.', 'Str.', 'Dr.'];

    public getResidencesByPersonId(personId: number): Observable<Address[]> {
        return this.httpClient.get<Address[]>(`${environment.apiUrl}/residences/persons/${personId}/addresses`);
    }

    public getResidencesByPersonEmail(personEmail: string): Observable<Address[]> {
        return this.httpClient.get<Address[]>(`${environment.apiUrl}/residences/persons/email/${personEmail}/addresses`);
    }

    public addResidence(residenceValue: ResidenceValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/residence`, residenceValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}