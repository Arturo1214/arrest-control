import { Component } from '@angular/core';
import { CHARGED_SYSTEM } from 'app/shared/constants/input.constants';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent {
  chargedSystem = CHARGED_SYSTEM;
}
