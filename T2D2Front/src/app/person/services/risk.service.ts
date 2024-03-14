import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { environment } from "src/environments/environments";
import { RiskFactorsValue } from "../models/riskFactors.model";
import { RiskDisplay } from "../models/riskDisplay.model";

@Injectable()
export class RiskService {
    constructor(
        private httpClient: HttpClient
    ) { }

    private _riskIsChecked$ = new BehaviorSubject<boolean>(true);
    get riskIsChecked$(): Observable<boolean> {
        return this._riskIsChecked$.asObservable();
    }


    private _risk$ = new BehaviorSubject<RiskDisplay>(new RiskDisplay);
    public get risk$(): Observable<RiskDisplay> {
        return this._risk$.asObservable();
    }

    public setRiskCheckingStatus(riskIsChecked: boolean) {
        this._riskIsChecked$.next(riskIsChecked)
    }

    public getRiskScore(riskFactors: RiskFactorsValue) {
        this.httpClient.post<RiskDisplay>(`${environment.msDiabetesUrl}/risk`, riskFactors).pipe(
            tap(risk => {
                this._risk$.next(risk);
            })
        ).subscribe();
    }
}