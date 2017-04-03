import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import {JobsComponent} from "../jobs/jobs.component";
import {JobsExecutionComponent} from "../jobs/jobs-execution/jobs-execution.component";
import {JobsMetaDataComponent} from "../jobs/jobs-meta-data/jobs-meta-data.component";
import {HomeComponent} from "../home/home.component";


export const routes: Routes = [
  { path: '' , pathMatch: 'full', redirectTo: 'home'},
  { path: 'home', component: HomeComponent},
  { path: 'job', component: JobsComponent},
  { path: 'executions', component: JobsExecutionComponent},
  { path: 'info', component: JobsMetaDataComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  declarations: [],
  exports: [RouterModule]
})

export class AppRoutingModule { }

// export const routingComponents = [JobsComponent];
