import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { Person } from "src/app/person/models/person.model";
import { environment } from "src/app/environments/environment";
import { PatientValue } from "../models/patient.model";

@Injectable()
export class PatientService {

    constructor(
        private httpClient: HttpClient
    ) { }

    private _patients$ = new BehaviorSubject<Person[]>([]);
    public get patients$(): Observable<Person[]> {
        return this._patients$.asObservable();
    }

    private _notPatients$ = new BehaviorSubject<Person[]>([]);
    public get notPatients$(): Observable<Person[]> {
        return this._notPatients$.asObservable();
    }

    public getPatientsByPractitionerId(practitionerId: number) {
        this.httpClient.get<Person[]>(`${environment.msPatientUrl}/patients/practitioners/${practitionerId}/persons`).pipe(
            tap(patients => {
                this._patients$.next(patients);
            })
        ).subscribe();
    }

    public getNotPatientsByPractitionerId(practitionerId: number) {
        this.httpClient.get<Person[]>(`${environment.msPatientUrl}/patients/practitioners/${practitionerId}/persons/not-patients`).pipe(
            tap(notPatients => {
                this._notPatients$.next(notPatients);
            })
        ).subscribe();
    }

    public addPatient(patientValue: PatientValue): Observable<boolean> {
        return this.httpClient.post(`${environment.msPatientUrl}/patient`, patientValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }

    public deletePatient(practitionerId: number, personId: number) {
        return this.httpClient.delete(`${environment.msPatientUrl}/patients/practitioners/${practitionerId}/persons/${personId}`);
    }
}