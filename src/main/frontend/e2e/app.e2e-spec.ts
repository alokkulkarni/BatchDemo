import { BatchDemoPage } from './app.po';

describe('batch-demo App', () => {
  let page: BatchDemoPage;

  beforeEach(() => {
    page = new BatchDemoPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
