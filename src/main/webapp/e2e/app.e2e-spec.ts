import { BatchPage } from './app.po';

describe('batch App', () => {
  let page: BatchPage;

  beforeEach(() => {
    page = new BatchPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
