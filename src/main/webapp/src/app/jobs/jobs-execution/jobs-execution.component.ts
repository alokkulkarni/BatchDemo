import { Component, OnInit } from '@angular/core';
import {JobsService} from "../jobs.service";

@Component({
  selector: 'app-jobs-execution',
  templateUrl: './jobs-execution.component.html',
  styleUrls: ['./jobs-execution.component.css'],
  providers: [JobsService]
})
export class JobsExecutionComponent implements OnInit {


  jobsExecutionInfo = [];

  constructor(private jobService: JobsService) { }

  ngOnInit() {
    this.getJobsExecution();
  }

  getJobsExecution() {
    this.jobService.getJobExecution().subscribe(jobsExecutionInfoData => this.jobsExecutionInfo = jobsExecutionInfoData);
  }

  stopAllJobs() {

  }

  stopJob(jobExecutionId: string) {

  }

  restartJob(jobExecutionId: string) {

  }
}
