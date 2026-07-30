import { Directive, ViewContainerRef, inject, TemplateRef, input, effect } from '@angular/core';

@Directive({
  selector: '[isLoggedIn]',
})
export class Profile {
  private readonly _viewContainerRef: ViewContainerRef = inject(ViewContainerRef);
  private readonly _templateRef = this._viewContainerRef.injector.get(TemplateRef);

  isLoggedIn = input<boolean>(false);

  constructor() {
    effect(() => {
      if (this.isLoggedIn()) {
        this._viewContainerRef.createEmbeddedView(this._templateRef);
      } else {
        this._viewContainerRef.clear();
      }
    });
  }
}
