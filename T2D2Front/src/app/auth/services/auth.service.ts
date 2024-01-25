import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { AuthValue } from "../models/auth.model";
import { Practitioner } from "src/app/core/models/practitioner.model";

@Injectable()
export class AuthService {

    constructor(
        private httpClient: HttpClient
    ) { }

    public getPractitionerById(practitionerId: number): Observable<Practitioner> {
        return this.httpClient.get<Practitioner>(`${environment.apiUrl}/practitioners/${practitionerId}`);
    }

    public login(authValue: AuthValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/login`, authValue).pipe(
            tap((apiResponse) => {
                sessionStorage.setItem('authToken', this.setToken(512));
                sessionStorage.setItem('currentPractitionerId', JSON.parse(JSON.stringify(apiResponse)).id);
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }

    public isLogged(): boolean {
        return sessionStorage.getItem('currentPractitionerId') ? true : false;
    }

    public logout(): boolean {
        sessionStorage.removeItem('authToken');
        sessionStorage.removeItem('currentPractitionerId');
        
        return (sessionStorage.getItem('authToken') == null && sessionStorage.getItem('currentPractitionerId') == null);
    }

    public setToken(length: number): string {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        const randomValues = new Uint32Array(length);
        let result = '';

        window.crypto.getRandomValues(randomValues);
        randomValues.forEach((value) => { result += characters.charAt(value % characters.length) });

        return result;
    }

    public getToken() {
        let authToken = sessionStorage.getItem('authToken');
        return authToken;
    }
}