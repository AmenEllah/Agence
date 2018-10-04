/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgenceTestModule } from '../../../test.module';
import { EnvironnementDeleteDialogComponent } from 'app/entities/environnement/environnement-delete-dialog.component';
import { EnvironnementService } from 'app/entities/environnement/environnement.service';

describe('Component Tests', () => {
    describe('Environnement Management Delete Component', () => {
        let comp: EnvironnementDeleteDialogComponent;
        let fixture: ComponentFixture<EnvironnementDeleteDialogComponent>;
        let service: EnvironnementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [EnvironnementDeleteDialogComponent]
            })
                .overrideTemplate(EnvironnementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvironnementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironnementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
