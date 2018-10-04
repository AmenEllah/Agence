/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AgenceTestModule } from '../../../test.module';
import { ConfortComponent } from 'app/entities/confort/confort.component';
import { ConfortService } from 'app/entities/confort/confort.service';
import { Confort } from 'app/shared/model/confort.model';

describe('Component Tests', () => {
    describe('Confort Management Component', () => {
        let comp: ConfortComponent;
        let fixture: ComponentFixture<ConfortComponent>;
        let service: ConfortService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [ConfortComponent],
                providers: []
            })
                .overrideTemplate(ConfortComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfortComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfortService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Confort(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.conforts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
