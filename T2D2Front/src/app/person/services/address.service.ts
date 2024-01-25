import { Injectable } from "@angular/core";
import { Address } from "../../core/models/address.model";
import { catchError, map, of } from "rxjs";
import { environment } from "src/app/environments/environment";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class AddressService {

    constructor(
        private httpClient: HttpClient
    ) {}

    public createNewAddress(newAddress: Address) {
        return this.httpClient.post<{id: number}>(`${environment.apiUrl}/address`, newAddress).pipe(
            map(newAdressId => newAdressId.id),
            catchError(() => of(0))
        );
    }

    public updateAddressById(addressId: number, addressesUpdate: Address) {
        return this.httpClient.put(`${environment.apiUrl}/address/${addressId}`, addressesUpdate).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}