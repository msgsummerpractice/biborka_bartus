import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'username',
})
export class UsernamePipe implements PipeTransform {
  transform(value: string | null): string {
    if (!value) {
      return '';
    }
    return `@${value.toLowerCase()}`;
  }
}
