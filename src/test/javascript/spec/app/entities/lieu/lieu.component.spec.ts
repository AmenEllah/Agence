/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AgenceTestModule } from '../../../test.module';
import { LieuComponent } from 'app/entities/lieu/lieu.component';
import { LieuService } from 'app/entities/lieu/lieu.service';
import { Lieu } from 'app/shared/model/lieu.model';

describe('Component Tests', () => {
    describe('Lieu Management Component', () => {
        let comp: LieuComponent;
        let fixture: ComponentFixture<LieuComponent>;
        let service: LieuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [LieuComponent],
                providers: []
            })
                .overrideTemplate(LieuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LieuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Lieu(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lieus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
