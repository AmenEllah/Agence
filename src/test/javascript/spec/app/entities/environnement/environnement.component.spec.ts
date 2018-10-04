/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AgenceTestModule } from '../../../test.module';
import { EnvironnementComponent } from 'app/entities/environnement/environnement.component';
import { EnvironnementService } from 'app/entities/environnement/environnement.service';
import { Environnement } from 'app/shared/model/environnement.model';

describe('Component Tests', () => {
    describe('Environnement Management Component', () => {
        let comp: EnvironnementComponent;
        let fixture: ComponentFixture<EnvironnementComponent>;
        let service: EnvironnementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [EnvironnementComponent],
                providers: []
            })
                .overrideTemplate(EnvironnementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvironnementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironnementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Environnement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.environnements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
