import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

/* local offline storage module */
import { Ng2Webstorage } from 'ng2-webstorage';

/* Routing Module */
import { AppRoutingModule } from './app-routing/app-routing.module';

/* Shaed Module */
import { SharedModule } from './shared/shared.module';

/* Jobs Module*/
import { JobsModule } from './jobs/jobs.module';

/* Home Module */
import { HomeModule } from './home/home.module';

import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    SharedModule,
    Ng2Webstorage.forRoot({ prefix: 'pbe', separator: '-'}),
    NgbModule.forRoot(),
    JobsModule,
    HomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
