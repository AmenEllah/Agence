/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { HebergementDetailComponent } from 'app/entities/hebergement/hebergement-detail.component';
import { Hebergement } from 'app/shared/model/hebergement.model';

describe('Component Tests', () => {
    describe('Hebergement Management Detail Component', () => {
        let comp: HebergementDetailComponent;
        let fixture: ComponentFixture<HebergementDetailComponent>;
        const route = ({ data: of({ hebergement: new Hebergement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [HebergementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HebergementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HebergementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hebergement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
