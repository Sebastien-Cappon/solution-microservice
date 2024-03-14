import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { Factor } from "../models/factor.model";
import { environment } from "src/environments/environments";

@Injectable()
export class FactorService {

    constructor(
        private httpclient: HttpClient
    ) { }

    private _factors$ = new BehaviorSubject<Factor[]>([]);
    public get factors$(): Observable<Factor[]> {
        return this._factors$.asObservable();
    }

    public getFactors() {
        this.httpclient.get<Factor[]>(`${environment.msDiabetesUrl}/triggers`).pipe(
            tap(factors => {
                this._factors$.next(factors);
            })
        ).subscribe();
    }

    public addNewFactor(newFactor: Factor): Observable<boolean> {
        return this.httpclient.post(`${environment.msDiabetesUrl}/trigger`, newFactor).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }

    public deleteFactorById(factorId: number) {
        return this.httpclient.delete(`${environment.msDiabetesUrl}/triggers/${factorId}`);
    }
}