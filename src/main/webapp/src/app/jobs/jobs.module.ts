import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { JobsComponent } from './jobs.component';
import { JobsExecutionComponent } from './jobs-execution/jobs-execution.component';
import { JobsMetaDataComponent } from './jobs-meta-data/jobs-meta-data.component';
import { JobsStepExecutionComponent } from './jobs-step-execution/jobs-step-execution.component'


@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [JobsComponent, JobsExecutionComponent, JobsMetaDataComponent, JobsStepExecutionComponent],
  exports: [JobsComponent, JobsExecutionComponent, JobsMetaDataComponent, JobsStepExecutionComponent]
})
export class JobsModule { }
