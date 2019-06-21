import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ContactDetail} from "./model/contact-detail";

@Injectable({
  providedIn: 'root'
})
export class ContactHttpService {

  constructor(private httpClient: HttpClient) {
  }

  getObservableContactDetail(): Observable<ContactDetail[]> {
    return this.httpClient.get<ContactDetail[]>("/api/contact/all");
  }

  searchByFullName(fullName: string): Observable<ContactDetail> {
    return this.httpClient.get<ContactDetail>("/api/contact/by-name")
  }

  searchByPhone(phone: string): Observable<ContactDetail> {
    return this.httpClient.get<ContactDetail>("/api/contact/by-phone")
  }
}
