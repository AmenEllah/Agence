/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgenceTestModule } from '../../../test.module';
import { EnvironnementDetailComponent } from 'app/entities/environnement/environnement-detail.component';
import { Environnement } from 'app/shared/model/environnement.model';

describe('Component Tests', () => {
    describe('Environnement Management Detail Component', () => {
        let comp: EnvironnementDetailComponent;
        let fixture: ComponentFixture<EnvironnementDetailComponent>;
        const route = ({ data: of({ environnement: new Environnement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [EnvironnementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnvironnementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvironnementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.environnement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
