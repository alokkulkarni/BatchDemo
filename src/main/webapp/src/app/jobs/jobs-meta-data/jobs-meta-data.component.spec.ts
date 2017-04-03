import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsMetaDataComponent } from './jobs-meta-data.component';

describe('JobsMetaDataComponent', () => {
  let component: JobsMetaDataComponent;
  let fixture: ComponentFixture<JobsMetaDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobsMetaDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsMetaDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
