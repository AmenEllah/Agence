/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { ConfortUpdateComponent } from 'app/entities/confort/confort-update.component';
import { ConfortService } from 'app/entities/confort/confort.service';
import { Confort } from 'app/shared/model/confort.model';

describe('Component Tests', () => {
    describe('Confort Management Update Component', () => {
        let comp: ConfortUpdateComponent;
        let fixture: ComponentFixture<ConfortUpdateComponent>;
        let service: ConfortService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [ConfortUpdateComponent]
            })
                .overrideTemplate(ConfortUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfortUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfortService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Confort(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.confort = entity;
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
                    const entity = new Confort();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.confort = entity;
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
