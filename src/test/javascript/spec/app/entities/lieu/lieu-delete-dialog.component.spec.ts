/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgenceTestModule } from '../../../test.module';
import { LieuDeleteDialogComponent } from 'app/entities/lieu/lieu-delete-dialog.component';
import { LieuService } from 'app/entities/lieu/lieu.service';

describe('Component Tests', () => {
    describe('Lieu Management Delete Component', () => {
        let comp: LieuDeleteDialogComponent;
        let fixture: ComponentFixture<LieuDeleteDialogComponent>;
        let service: LieuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [LieuDeleteDialogComponent]
            })
                .overrideTemplate(LieuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LieuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuService);
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
