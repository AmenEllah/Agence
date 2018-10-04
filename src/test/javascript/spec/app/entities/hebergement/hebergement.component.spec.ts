/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AgenceTestModule } from '../../../test.module';
import { HebergementComponent } from 'app/entities/hebergement/hebergement.component';
import { HebergementService } from 'app/entities/hebergement/hebergement.service';
import { Hebergement } from 'app/shared/model/hebergement.model';

describe('Component Tests', () => {
    describe('Hebergement Management Component', () => {
        let comp: HebergementComponent;
        let fixture: ComponentFixture<HebergementComponent>;
        let service: HebergementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [HebergementComponent],
                providers: []
            })
                .overrideTemplate(HebergementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HebergementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HebergementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Hebergement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hebergements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
