import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { Patient } from "src/app/core/models/person.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class PatientsService {

    constructor(
        private httpClient: HttpClient
    ) { }

    private _patients$ = new BehaviorSubject<Patient[]>([]);
    get patients$(): Observable<Patient[]> {
        return this._patients$.asObservable();
    }

    getPatientsByPractitioner(practitionerId : number) {
        this.httpClient.get<Patient[]>(`${environment.apiUrl}/${practitionerId}/patients`);
    }
}