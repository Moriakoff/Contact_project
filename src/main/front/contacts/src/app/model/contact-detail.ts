import {Address} from "./address";

export class ContactDetail {

  constructor(fullName: string, email: string, phoneNumbers: string[], addresses: Address[]) {
    this._fullName = fullName;
    this._email = email;
    this._phoneNumbers = phoneNumbers;
    this._addresses = addresses;
  }

  private _fullName: string;

  get fullName(): string {
    return this._fullName;
  }

  set fullName(value: string) {
    this._fullName = value;
  }

  private _email: string;

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  private _phoneNumbers: string[];

  get phoneNumbers(): string[] {
    return this._phoneNumbers;
  }

  set phoneNumbers(value: string[]) {
    this._phoneNumbers = value;
  }

  private _addresses: Address[];

  get addresses(): Address[] {
    return this._addresses;
  }

  set addresses(value: Address[]) {
    this._addresses = value;
  }
}
