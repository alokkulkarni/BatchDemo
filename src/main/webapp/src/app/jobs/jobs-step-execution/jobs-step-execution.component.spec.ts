import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsStepExecutionComponent } from './jobs-step-execution.component';

describe('JobsStepExecutionComponent', () => {
  let component: JobsStepExecutionComponent;
  let fixture: ComponentFixture<JobsStepExecutionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobsStepExecutionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsStepExecutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
