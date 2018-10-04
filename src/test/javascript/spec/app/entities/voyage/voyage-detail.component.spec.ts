/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { VoyageDetailComponent } from 'app/entities/voyage/voyage-detail.component';
import { Voyage } from 'app/shared/model/voyage.model';

describe('Component Tests', () => {
    describe('Voyage Management Detail Component', () => {
        let comp: VoyageDetailComponent;
        let fixture: ComponentFixture<VoyageDetailComponent>;
        const route = ({ data: of({ voyage: new Voyage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [VoyageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VoyageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoyageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.voyage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
