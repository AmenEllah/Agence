/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AgenceTestModule } from '../../../test.module';
import { VoyageComponent } from 'app/entities/voyage/voyage.component';
import { VoyageService } from 'app/entities/voyage/voyage.service';
import { Voyage } from 'app/shared/model/voyage.model';

describe('Component Tests', () => {
    describe('Voyage Management Component', () => {
        let comp: VoyageComponent;
        let fixture: ComponentFixture<VoyageComponent>;
        let service: VoyageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [VoyageComponent],
                providers: []
            })
                .overrideTemplate(VoyageComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VoyageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoyageService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Voyage(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.voyages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
