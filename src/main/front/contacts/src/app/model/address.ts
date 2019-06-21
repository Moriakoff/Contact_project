export class Address {

  constructor(country: string, city: string, street: string, houseNumber: string, apartment: string) {
    this._country = country;
    this._city = city;
    this._street = street;
    this._houseNumber = houseNumber;
    this._apartment = apartment;
  }

  private _country: string;

  get country(): string {
    return this._country;
  }

  set country(value: string) {
    this._country = value;
  }

  private _city: string;

  get city(): string {
    return this._city;
  }

  set city(value: string) {
    this._city = value;
  }

  private _street: string;

  get street(): string {
    return this._street;
  }

  set street(value: string) {
    this._street = value;
  }

  private _houseNumber: string;

  get houseNumber(): string {
    return this._houseNumber;
  }

  set houseNumber(value: string) {
    this._houseNumber = value;
  }

  private _apartment: string;

  get apartment(): string {
    return this._apartment;
  }

  set apartment(value: string) {
    this._apartment = value;
  }
}
