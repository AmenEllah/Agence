/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { HebergementUpdateComponent } from 'app/entities/hebergement/hebergement-update.component';
import { HebergementService } from 'app/entities/hebergement/hebergement.service';
import { Hebergement } from 'app/shared/model/hebergement.model';

describe('Component Tests', () => {
    describe('Hebergement Management Update Component', () => {
        let comp: HebergementUpdateComponent;
        let fixture: ComponentFixture<HebergementUpdateComponent>;
        let service: HebergementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [HebergementUpdateComponent]
            })
                .overrideTemplate(HebergementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HebergementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HebergementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hebergement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hebergement = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hebergement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hebergement = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
