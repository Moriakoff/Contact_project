import {Component, OnInit} from '@angular/core';
import {ContactDetail} from "../../model/contact-detail";
import {ContactHttpService} from "../../contact-http.service";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {


  private contacts: ContactDetail [] = [];
  private fullName: string;
  private phoneNumber: string;


  constructor(private contactHttpService: ContactHttpService) {
  }


  getContacts() {
    return this.contactHttpService
      .getObservableContactDetail()
      .subscribe(data => this.contacts = data);
  }


  ngOnInit() {
    this.getContacts();
  }

  searchByName(fullName: string) {
    return this.contactHttpService
      .searchByFullName(fullName)
      .subscribe()
  }

  searchByPhone(phone: string) {
    return this.contactHttpService
      .searchByPhone(phone)
      .subscribe();
  }
}
