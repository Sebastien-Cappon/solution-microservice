import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { ResidenceValue } from "../models/residence.model";
import { Address } from "src/app/person/models/address.model";

@Injectable()
export class ResidenceService {

    constructor(
        private httpClient: HttpClient
    ) { }

    private _residences$ = new BehaviorSubject<Address[]>([]);
    public get residences$(): Observable<Address[]> {
        return this._residences$.asObservable();
    }

    public wayTypes: string[] = ['Ave.', 'Rd.', 'Str.', 'Dr.'];

    public getResidencesByPersonId(personId: number) {
        this.httpClient.get<Address[]>(`${environment.msPatientUrl}/residences/persons/${personId}/addresses`).pipe(
            tap(residences => {
                this._residences$.next(residences);
            })
        ).subscribe();
    }

    public getResidencesByPersonEmail(personEmail: string): Observable<Address[]> {
        return this.httpClient.get<Address[]>(`${environment.msPatientUrl}/residences/persons/email/${personEmail}/addresses`);
    }

    public addResidence(residenceValue: ResidenceValue): Observable<boolean> {
        return this.httpClient.post(`${environment.msPatientUrl}/residence`, residenceValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}