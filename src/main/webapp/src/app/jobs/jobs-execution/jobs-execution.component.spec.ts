import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsExecutionComponent } from './jobs-execution.component';

describe('JobsExecutionComponent', () => {
  let component: JobsExecutionComponent;
  let fixture: ComponentFixture<JobsExecutionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobsExecutionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsExecutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
