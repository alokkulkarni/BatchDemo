import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'



@Injectable()
export class JobsService {

  private resourceUrl: string = "/api/jobs";


  constructor(private http:Http) { }

  getJobsInfo() {
    return this.http.get(`${this.resourceUrl}`).map((res: Response) => res.json());
  }

  getJobExecution() {
    return this.http.get(`${this.resourceUrl}/executions`).map((res: Response) => res.json());
  }

  getJobExecutionDetail(jobExecutionId : string) {
    return this.http.get(`${this.resourceUrl}/executions/${jobExecutionId}`).map((res: Response) => res.json());
  }

}
