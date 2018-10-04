/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { ConfortDetailComponent } from 'app/entities/confort/confort-detail.component';
import { Confort } from 'app/shared/model/confort.model';

describe('Component Tests', () => {
    describe('Confort Management Detail Component', () => {
        let comp: ConfortDetailComponent;
        let fixture: ComponentFixture<ConfortDetailComponent>;
        const route = ({ data: of({ confort: new Confort(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [ConfortDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConfortDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfortDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.confort).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
