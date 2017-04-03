import { Component, OnInit } from '@angular/core';
import  { JobsService } from './jobs.service'

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css'],
  providers: [JobsService]
})
export class JobsComponent implements OnInit {

  private jobsInfo = [];

  constructor(
    private jobService: JobsService
  ) { }

  ngOnInit() {
    this.getJobs();
  }

  getJobs() {
    this.jobService.getJobsInfo().subscribe(jobsInfoData => this.jobsInfo = jobsInfoData);
  }
}
