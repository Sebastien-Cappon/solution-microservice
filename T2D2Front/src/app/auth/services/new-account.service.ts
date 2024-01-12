import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { NewPractitionerValue } from "../models/new-practitioner.model";

@Injectable()
export class NewAccountService {
    
    constructor(
        private httpClient: HttpClient,
        private authService: AuthService
    ) { }

    createNewAccount(newPractitionerValue: NewPractitionerValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/practitioners/practitioner/create`, newPractitionerValue).pipe(
            tap((apiResponse) => {
                sessionStorage.setItem('authToken', this.authService.setToken(512));
                sessionStorage.setItem('currentPractitionerId', JSON.parse(JSON.stringify(apiResponse)).id);
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }
}