import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ArrestControlTestModule } from '../../../test.module';
import { ArrestUpdateComponent } from 'app/entities/arrest/arrest-update.component';
import { ArrestService } from 'app/entities/arrest/arrest.service';
import { Arrest } from 'app/shared/model/arrest.model';

describe('Component Tests', () => {
  describe('Arrest Management Update Component', () => {
    let comp: ArrestUpdateComponent;
    let fixture: ComponentFixture<ArrestUpdateComponent>;
    let service: ArrestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArrestControlTestModule],
        declarations: [ArrestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArrestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArrestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArrestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Arrest(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Arrest();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
