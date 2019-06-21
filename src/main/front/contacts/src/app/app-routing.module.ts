import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactUpdateComponent} from "./contact/contact-update/contact-update.component";
import {InfoComponent} from "./contact/info/info.component";

const routes: Routes = [
  {
    path: "",
    component: InfoComponent
  },
  {
    path: "update",
    component: ContactUpdateComponent
  },
  {
    path: "info/all",
    component: InfoComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
