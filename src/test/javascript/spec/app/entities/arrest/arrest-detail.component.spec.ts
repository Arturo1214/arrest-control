import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArrestControlTestModule } from '../../../test.module';
import { ArrestDetailComponent } from 'app/entities/arrest/arrest-detail.component';
import { Arrest } from 'app/shared/model/arrest.model';

describe('Component Tests', () => {
  describe('Arrest Management Detail Component', () => {
    let comp: ArrestDetailComponent;
    let fixture: ComponentFixture<ArrestDetailComponent>;
    const route = ({ data: of({ arrest: new Arrest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArrestControlTestModule],
        declarations: [ArrestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArrestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArrestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load arrest on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arrest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
