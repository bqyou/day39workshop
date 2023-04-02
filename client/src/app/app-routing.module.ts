import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CharacterdetailsComponent } from './components/characterdetails.component';
import { SearchbarComponent } from './components/searchbar.component';
import { SearchresultComponent } from './components/searchresult.component';

const routes: Routes = [
  {path:'', component:SearchbarComponent},
  {path:'list/:searchphrase', component:SearchresultComponent},
  {path:'details/:charId', component:CharacterdetailsComponent},
  {path:'**', redirectTo:'', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
