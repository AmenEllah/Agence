/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { EnvironnementUpdateComponent } from 'app/entities/environnement/environnement-update.component';
import { EnvironnementService } from 'app/entities/environnement/environnement.service';
import { Environnement } from 'app/shared/model/environnement.model';

describe('Component Tests', () => {
    describe('Environnement Management Update Component', () => {
        let comp: EnvironnementUpdateComponent;
        let fixture: ComponentFixture<EnvironnementUpdateComponent>;
        let service: EnvironnementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [EnvironnementUpdateComponent]
            })
                .overrideTemplate(EnvironnementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvironnementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironnementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Environnement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.environnement = entity;
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
                    const entity = new Environnement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.environnement = entity;
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
