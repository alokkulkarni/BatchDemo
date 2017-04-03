/**
 * Created by alokkulkarni on 23/03/17.
 */
import { Directive, ElementRef } from '@angular/core';

@Directive({ selector: '[highlight], input' })
/** Highlight the attached element or an InputElement in gray */
export class HighlightDirective {
  constructor(el: ElementRef) {
    el.nativeElement.style.backgroundColor = 'lightgray';
    console.log(
      `* Shared highlight called for ${el.nativeElement.tagName}`);
  }
}
