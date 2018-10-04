/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { VoyageUpdateComponent } from 'app/entities/voyage/voyage-update.component';
import { VoyageService } from 'app/entities/voyage/voyage.service';
import { Voyage } from 'app/shared/model/voyage.model';

describe('Component Tests', () => {
    describe('Voyage Management Update Component', () => {
        let comp: VoyageUpdateComponent;
        let fixture: ComponentFixture<VoyageUpdateComponent>;
        let service: VoyageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [VoyageUpdateComponent]
            })
                .overrideTemplate(VoyageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VoyageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoyageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Voyage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voyage = entity;
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
                    const entity = new Voyage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.voyage = entity;
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
